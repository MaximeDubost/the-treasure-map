package fr.mdbs.thetreasuremap.application.service.game.impl;

import fr.mdbs.thetreasuremap.adapter.inbound.FileParser;
import fr.mdbs.thetreasuremap.application.GameManager;
import fr.mdbs.thetreasuremap.application.exception.UnexistingTileMapException;
import fr.mdbs.thetreasuremap.application.service.game.GameService;
import fr.mdbs.thetreasuremap.domain.model.tilemap.TileMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<TileMap> tileMap;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            tileMap = FileParser.parseLines(reader.lines().toList());
        }
        if(tileMap.isEmpty())
            throw new UnexistingTileMapException("No processable map (\"C\" line) found in input file");
        return new GameManager(tileMap.get()).process();
    }
}
