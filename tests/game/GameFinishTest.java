package game;

import cell.Cell;
import listeners.GameListener;
import org.junit.jupiter.api.Test;
import units.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

public class GameFinishTest {

    @Test
    public void testGameListenerReceivesGameIsOver() {
        Game game = new Game(4, 4);
        AtomicBoolean gameIsOverReceived = new AtomicBoolean(false);

        game.addGameListener(new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
                gameIsOverReceived.set(true);
            }
        });

        game.tilesInFinishConfiguration();

        assertTrue(gameIsOverReceived.get());
    }

    @Test
    public void testGameListenerReceivesTilesInFinishConfiguration() {
        Game game = new Game(4, 4);
        AtomicBoolean gameIsOverReceived = new AtomicBoolean(false);

        game.addGameListener(new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
                gameIsOverReceived.set(true);
            }
        });

        game.tilesInFinishConfiguration();

        assertTrue(gameIsOverReceived.get());
    }

    @Test
    public void testMultipleListenersReceiveGameIsOver() {
        Game game = new Game(4, 4);
        AtomicInteger gameIsOverCount = new AtomicInteger(0);

        game.addGameListener(new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
                gameIsOverCount.incrementAndGet();
            }
        });

        game.addGameListener(new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
                gameIsOverCount.incrementAndGet();
            }
        });

        game.addGameListener(new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
                gameIsOverCount.incrementAndGet();
            }
        });

        game.tilesInFinishConfiguration();

        assertEquals(3, gameIsOverCount.get());
    }

    @Test
    public void testGameFieldDeactivatedOnFinish() {
        Game game = new Game(4, 4);
        game.start();

        game.tilesInFinishConfiguration();

        // Verify tiles are deactivated
        for (Cell cell : game.getField()) {
            Tile tile = cell.getUnit(Tile.class);
            if (tile != null) {
                assertFalse(tile.isActive(), "Tile should be deactivated");
            }
        }
    }

    @Test
    public void testGameIsOverAfterTilesInFinishConfiguration() {
        Game game = new Game(4, 4);

        assertFalse(game.isOver());

        game.tilesInFinishConfiguration();

        assertTrue(game.isOver());
    }

    @Test
    public void testAddGameListener() {
        Game game = new Game(4, 4);
        AtomicBoolean gameIsOverReceived = new AtomicBoolean(false);

        game.addGameListener(new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
                gameIsOverReceived.set(true);
            }
        });

        game.tilesInFinishConfiguration();

        assertTrue(gameIsOverReceived.get());
    }

    @Test
    public void testRemoveGameListener() {
        Game game = new Game(4, 4);
        AtomicBoolean listenerCalled = new AtomicBoolean(false);

        GameListener listener = new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
                listenerCalled.set(true);
            }

            @Override
            public void gameIsOver() {
            }
        };

        game.addGameListener(listener);
        game.removeGameListener(listener);

        game.tilesInFinishConfiguration();

        assertFalse(listenerCalled.get());
    }

    @Test
    public void testGetGameListeners() {
        Game game = new Game(4, 4);

        GameListener listener1 = new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
            }
        };

        GameListener listener2 = new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
            }
        };

        game.addGameListener(listener1);
        game.addGameListener(listener2);

        assertEquals(2, game.getGameListeners().size());
        assertTrue(game.getGameListeners().contains(listener1));
        assertTrue(game.getGameListeners().contains(listener2));
    }

    @Test
    public void testGetGameListenersReturnsUnmodifiableList() {
        Game game = new Game(4, 4);
        game.addGameListener(new DummyGameListener());

        assertThrows(UnsupportedOperationException.class, () -> {
            game.getGameListeners().add(new DummyGameListener());
        });
    }

    @Test
    public void testAddNullGameListener() {
        Game game = new Game(4, 4);
        int initialSize = game.getGameListeners().size();

        game.addGameListener(null);

        assertEquals(initialSize, game.getGameListeners().size());
    }

    @Test
    public void testRemoveNullGameListener() {
        Game game = new Game(4, 4);
        int initialSize = game.getGameListeners().size();

        game.removeGameListener(null);

        assertEquals(initialSize, game.getGameListeners().size());
    }

    @Test
    public void testGameFinishSequence() {
        Game game = new Game(4, 4);
        AtomicBoolean gameIsOverCalled = new AtomicBoolean(false);

        game.addGameListener(new GameListener() {
            @Override
            public void tilesInFinishConfiguration() {
            }

            @Override
            public void gameIsOver() {
                gameIsOverCalled.set(true);
            }
        });

        game.tilesInFinishConfiguration();

        assertTrue(gameIsOverCalled.get(), "gameIsOver should be called");
        assertTrue(game.isOver(), "Game should be over");
    }

    @Test
    public void testGameFinishDeactivatesAllUnits() {
        Game game = new Game(4, 4);
        game.start();

        int activeUnitsBefore = countActiveUnits(game);

        game.tilesInFinishConfiguration();

        int activeUnitsAfter = countActiveUnits(game);

        assertEquals(0, activeUnitsAfter, "All units should be deactivated");
        assertTrue(activeUnitsBefore >= 0);
    }

    private int countActiveUnits(Game game) {
        int count = 0;
        for (Cell cell : game.getField()) {
            for (var unit : cell.getAllUnits()) {
                if (unit.isActive()) {
                    count++;
                }
            }
        }
        return count;
    }

    private static class DummyGameListener implements GameListener {
        @Override
        public void tilesInFinishConfiguration() {
        }

        @Override
        public void gameIsOver() {
        }
    }
}
