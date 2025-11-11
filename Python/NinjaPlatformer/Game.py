import os
import pygame

from os.path import join

from Scripts.Clouds import Clouds
from Scripts.Entities import PhysicsEntity
from Scripts.Tilemap import Tilemap

from Scripts.Utils import load_image, load_images

class Game:
    def __init__(self):
        os.environ['SDL_VIDEO_CENTERED'] = '1'

        pygame.init()

        pygame.display.set_caption("Ninja Platformer")

        #self.screen = pygame.display.set_mode((1280, 720))
        #self.display = pygame.surface.Surface((640, 360))

        self.screen = pygame.display.set_mode((640, 480))
        self.display = pygame.Surface((320, 240))

        self.clock = pygame.time.Clock()

        self.movement = [False, False]

        self.assets = {
            "Decor": load_images(join("Tiles", "Decor")),
            "Grass": load_images(join("Tiles", "Grass")),
            "Large_Decor": load_images(join("Tiles", "Large_Decor")),
            "Stone": load_images(join("Tiles", "Stone")),
            "Player": load_image(join("Entities", "Player.png")),
            "Background": load_image("Background.png"),
            "Clouds": load_images("Clouds")
        }

        self.clouds = Clouds(self.assets["Clouds"], count=16)
        self.player = PhysicsEntity(self, "Player", (50, 50), (8, 15))
        self.tilemap = Tilemap(self, tile_size=16)

        self.scroll = [0, 0]

    def run(self):
        while True:
            self.display.blit(self.assets["Background"], (0, 0))

            self.scroll[0] += (self.player.rect().centerx - self.display.get_width() / 2 - self.scroll[0]) / 30
            self.scroll[1] += (self.player.rect().centery - self.display.get_height() / 2 - self.scroll[1]) / 30
            render_scroll = (int(self.scroll[0]), int(self.scroll[1]))

            self.clouds.update()
            self.clouds.render(self.display, offset=render_scroll)

            self.tilemap.render(self.display, offset=render_scroll)

            self.player.update(self.tilemap, (self.movement[1] - self.movement[0], 0))
            self.player.render(self.display, offset=render_scroll)

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    quit()

                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_a:
                        self.movement[0] = True
                    if event.key == pygame.K_d:
                        self.movement[1] = True
                    if event.key == pygame.K_SPACE:
                        self.player.velocity[1] = -3

                if event.type == pygame.KEYUP:
                    if event.key == pygame.K_a:
                        self.movement[0] = False
                    if event.key == pygame.K_d:
                        self.movement[1] = False

            self.screen.blit(pygame.transform.scale(self.display, self.screen.get_size()), (0, 0))
            pygame.display.update()
            self.clock.tick(60)

Game().run()