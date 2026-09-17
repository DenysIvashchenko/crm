package com.agro.crm.features.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse create(
            @RequestBody ContractDto dto
    ) {
        Contract contract = contractService.create(dto);
        return ContractResponse.from(contract);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ContractResponse createWithFile(
            @RequestPart("data") String dataJson,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws JsonProcessingException {
        ContractDto dto = objectMapper.readValue(dataJson, ContractDto.class);
        Contract contract = contractService.createWithOptionalFile(dto, file);

        return ContractResponse.from(contract);
    }

    @PostMapping("/{id}/upload")
    public ContractResponse uploadFile(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) {
        Contract contract = contractService.uploadSignedFile(id, file);
        return ContractResponse.from(contract);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        ContractService.DownloadableFile file = contractService.download(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.fileName() + "\"")
                .body(file.resource());
    }

    @GetMapping("/{id}")
    public ContractResponse getById(@PathVariable Long id) {
        return ContractResponse.from(contractService.getById(id));
    }

    @GetMapping
    public List<ContractResponse> getAll() {
        return contractService.getAll().stream()
                .map(ContractResponse::from)
                .toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        contractService.delete(id);
    }
}
