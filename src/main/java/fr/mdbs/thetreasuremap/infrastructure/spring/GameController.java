package fr.mdbs.thetreasuremap.infrastructure.spring;

import fr.mdbs.thetreasuremap.application.service.game.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
public class GameController {

    private static final Logger log = LoggerFactory.getLogger(GameController.class);

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ByteArrayResource> process(@RequestParam("file") MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        log.info("[GameController#process] {}", filename);
        if (filename == null || !filename.toLowerCase().endsWith(".txt"))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(gameService.process(file));
    }

}
