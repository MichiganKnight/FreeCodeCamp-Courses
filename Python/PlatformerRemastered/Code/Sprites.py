import pygame

from Settings import *

class Sprite(pygame.sprite.Sprite):
    def __init__(self, position, surf = pygame.Surface((TILE_SIZE, TILE_SIZE)), groups = None):
        super().__init__(groups)

        self.image = surf
        self.image.fill("White")
        self.rect = self.image.get_rect(topleft = position)
        self.old_rect = self.rect.copy()

class MovingSprite(Sprite):
    def __init__(self, groups, start_position, end_position, move_direction, speed):
        surf = pygame.Surface((200, 50))

        super().__init__(start_position, surf, groups)

        if move_direction == "X":
            self.rect.midleft = start_position
        else:
            self.rect.midtop = start_position

        self.start_position = start_position
        self.end_position = end_position

        # Movement
        self.direction = vector(1, 0) if move_direction == "X" else vector(0, 1)
        self.speed = speed
        self.move_direction = move_direction

    def check_border(self):
        if self.move_direction == "X":
            if self.rect.right >= self.end_position[0] and self.direction.x == 1:
                self.direction.x = -1
                self.rect.right = self.end_position[0]
            if self.rect.left <= self.start_position[0] and self.direction.x == -1:
                self.direction.x = 1
                self.rect.left = self.start_position[0]
        else:
            if self.rect.bottom >= self.end_position[1] and self.direction.y == 1:
                self.direction.y = -1
                self.rect.bottom = self.end_position[1]
            if self.rect.top <= self.start_position[1] and self.direction.y == -1:
                self.direction.y = 1
                self.rect.top = self.start_position[1]


    def update(self, dt):
        self.old_rect = self.rect.copy()
        self.rect.topleft += self.direction * self.speed * dt
        self.check_border()