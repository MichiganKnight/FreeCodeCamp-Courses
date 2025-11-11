import os
import pygame

from os.path import join

from Scripts.Entities import PhysicsEntity
from Scripts.Utils import load_image

class Game:
    def __init__(self):
        os.environ['SDL_VIDEO_CENTERED'] = '1'

        pygame.init()

        pygame.display.set_caption("Ninja Platformer")

        self.screen = pygame.display.set_mode((1280, 720))
        self.clock = pygame.time.Clock()

        self.movement = [False, False]

        self.assets = {
            "Player": load_image(join("Entities", "Player.png"))
        }

        self.player = PhysicsEntity(self, "Player", (50, 50), (8, 15))

    def run(self):
        while True:
            self.screen.fill((14, 219, 248))

            self.player.update((self.movement[1] - self.movement[0], 0))
            self.player.render(self.screen)

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    pygame.quit()
                    quit()

                if event.type == pygame.KEYDOWN:
                    if event.key == pygame.K_a:
                        self.movement[0] = True
                    if event.key == pygame.K_d:
                        self.movement[1] = True

                if event.type == pygame.KEYUP:
                    if event.key == pygame.K_a:
                        self.movement[0] = False
                    if event.key == pygame.K_d:
                        self.movement[1] = False

            pygame.display.update()
            self.clock.tick(60)

Game().run()