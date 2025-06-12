package fr.mdbs.thetreasuremap.application.service.game.impl;

import fr.mdbs.thetreasuremap.adapter.inbound.FileParser;
import fr.mdbs.thetreasuremap.application.service.game.GameService;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Service
public class GameServiceImpl implements GameService {

    private static final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

    @Override
    public ByteArrayResource process(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Optional<TileMap> tileMap = FileParser.parseLines(reader.lines().toList());
            log.info(tileMap.toString());
            String result = "TODO"; // TODO
            return new ByteArrayResource(result.getBytes());
        }
    }
}
