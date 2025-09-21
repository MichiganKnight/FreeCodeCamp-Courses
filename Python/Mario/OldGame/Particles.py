from os.path import join
from Utils import import_folder

import pygame

class ParticleEffect(pygame.sprite.Sprite):
    def __init__(self, pos, particle_type):
        super().__init__()

        self.frame_index = 0
        self.animation_speed = 0.5

        if particle_type == 'Jump':
            self.frames = import_folder(join("Assets", "Character", "Dust_Particles", "Jump"))
        if particle_type == 'Land':
            self.frames = import_folder(join("Assets", "Character", "Dust_Particles", "Land"))

        self.image = self.frames[self.frame_index]
        self.rect = self.image.get_rect(center = pos)

    def animate(self):
        self.frame_index += self.animation_speed
        if self.frame_index >= len(self.frames):
            self.kill()
        else:
            self.image = self.frames[int(self.frame_index)]

    def update(self, x_shift):
        self.animate()
        self.rect.x += x_shift