import pygame

from pytmx.util_pygame import load_pygame
from os.path import join

from Settings import *
from Level import Level
from Support import *

class Game:
    def __init__(self):
        pygame.init()
        self.display_surface = pygame.display.set_mode((WINDOW_WIDTH, WINDOW_HEIGHT))
        pygame.display.set_caption("Platformer Remastered")
        self.clock = pygame.time.Clock()
        self.import_assets()

        self.tmx_maps = {0: load_pygame(join("Data", "Levels", "Omni.tmx"))}
        self.current_stage = Level(self.tmx_maps[0], self.level_frames)

    def import_assets(self):
        self.level_frames = {
            "Flag": import_folder("Graphics", "Level", "Flag"),
            "Saw": import_folder("Graphics", "Enemies", "Saw", "Animation"),
            "Floor_Spike": import_folder("Graphics", "Enemies", "Floor_Spikes"),
            "Palms": import_sub_folders("Graphics", "Level", "Palms"),
            "Candle": import_folder("Graphics", "Level", "Candle"),
            "Window": import_folder("Graphics", "Level", "Window"),
            "Big_Chain": import_folder("Graphics", "Level", "Big_Chains"),
            "Small_Chain": import_folder("Graphics", "Level", "Small_Chains"),
            "Candle_Light": import_folder("Graphics", "Level", "Candle Light"),
            "Player": import_sub_folders("Graphics", "Player")
        }

    def run(self):
        while True:
            dt = self.clock.tick(120) / 1000
            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    sys.exit()

            self.current_stage.run(dt)
            pygame.display.update()

if __name__ == "__main__":
    game = Game()
    game.run()