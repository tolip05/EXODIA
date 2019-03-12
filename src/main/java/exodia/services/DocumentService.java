package exodia.services;

import exodia.models.service.DocumentServiceModel;

import java.util.List;

public interface DocumentService {
    DocumentServiceModel scheduleDocument(DocumentServiceModel documentServiceModel);
    DocumentServiceModel findDocumentById(String id);
    List<DocumentServiceModel> findAllDocument();
    boolean printDocument(String id);
}
