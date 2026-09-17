package com.agro.crm.features.contract;

import com.agro.crm.features.user.User;
import com.agro.crm.features.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractService {

    private final ContractRepository contractRepository;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;

    public Contract create(ContractDto dto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User manager = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Manager not found"));

        Contract contract = Contract.create(dto, manager);
        return contractRepository.save(contract);
    }

    public Contract createWithOptionalFile(ContractDto dto, MultipartFile file) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User manager = userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Manager not found"));

        Contract contract = Contract.create(dto, manager);
        contract = contractRepository.save(contract);

        if (file != null && !file.isEmpty()) {
            FileStorageService.StoredFile stored = fileStorageService.store(file, contract.getId());
            contract.attachSignedFile(stored.originalName(), stored.path(), stored.size());
            contract = contractRepository.save(contract);
        }

        return contract;
    }

    public Contract uploadSignedFile(Long contractId, MultipartFile file) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found: " + contractId));

        FileStorageService.StoredFile stored = fileStorageService.store(file, contractId);

        contract.attachSignedFile(stored.originalName(), stored.path(), stored.size());

        return contractRepository.save(contract);
    }

    @Transactional(readOnly = true)
    public DownloadableFile download(Long contractId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found: " + contractId));

        if (!contract.hasFile()) {
            throw new IllegalStateException("Contract has no signed file attached");
        }

        Resource resource = fileStorageService.load(contract.getFilePath());

        return new DownloadableFile(resource, contract.getFileName());
    }

    @Transactional(readOnly = true)
    public Contract getById(Long id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<Contract> getAll() {
        return contractRepository.findAll();
    }

    public void delete(Long id) {
        contractRepository.deleteById(id);
    }

    public record DownloadableFile(Resource resource, String fileName) {}
}
