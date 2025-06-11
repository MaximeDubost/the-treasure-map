package fr.mdbs.thetreasuremap.application.service.game;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GameService {
    ByteArrayResource process(MultipartFile file) throws IOException;
}
