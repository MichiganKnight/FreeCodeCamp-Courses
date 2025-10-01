import pygame

from Settings import *
from Timer import Timer

class Player(pygame.sprite.Sprite):
    def __init__(self, position, groups, collision_sprites):
        super().__init__(groups)

        self.image = pygame.Surface((48, 56))
        self.image.fill("Red")
        self.rect = self.image.get_rect(topleft = position)

        # Rects
        self.rect = self.image.get_rect(topleft = position)
        self.old_rect = self.rect.copy()

        # Movement
        self.direction = vector()
        self.speed = 200
        self.gravity = 1300
        self.jump = False
        self.jump_height = 900

        # Collision
        self.collision_sprites = collision_sprites
        self.on_surface = { "Floor": False, "Left": False, "Right": False }
        self.platform = None

        # Timer
        self.timers = {
            "Wall Jump": Timer(400),
            "Wall Slide Block": Timer(250)
        }

    def input(self):
        keys = pygame.key.get_pressed()
        input_vector = vector(0, 0)

        if not self.timers["Wall Jump"].active:
            if keys[pygame.K_d] or keys[pygame.K_RIGHT]:
                input_vector.x += 1
            elif keys[pygame.K_a] or keys[pygame.K_LEFT]:
                input_vector.x -= 1
            self.direction.x = input_vector.normalize().x if input_vector else input_vector.x

        if keys[pygame.K_SPACE]:
            self.jump = True

    def move(self, dt):
        # Horizontal
        self.rect.x += self.direction.x * self.speed * dt
        self.collision("Horizontal")

        # Vertical
        if not self.on_surface["Floor"] and any((self.on_surface["Left"], self.on_surface["Right"])) and not self.timers["Wall Slide Block"].active:
            self.direction.y = 0
            self.rect.y += self.gravity / 10 * dt
        else:
            self.direction.y += self.gravity / 2 * dt
            self.rect.y += self.direction.y * dt
            self.direction.y += self.gravity / 2 * dt

        # Jump
        if self.jump:
            if self.on_surface["Floor"]:
                self.direction.y = -self.jump_height
                self.timers["Wall Slide Block"].activate()
                self.rect.bottom -= 1
            elif any((self.on_surface["Left"], self.on_surface["Right"])) and not self.timers["Wall Slide Block"].active:
                self.timers["Wall Jump"].activate()
                self.direction.y = -self.jump_height
                self.direction.x = 1 if self.on_surface["Left"] else -1
            self.jump = False

        self.collision("Vertical")

    def platform_move(self, dt):
        if self.platform:
            self.rect.topleft += self.platform.direction * self.platform.speed * dt

    def check_contact(self):
        floor_rect = pygame.Rect(self.rect.bottomleft, (self.rect.width, 2))
        right_rect = pygame.Rect(self.rect.topright + vector(0, self.rect.height / 4), (2, self.rect.height / 2))
        left_rect = pygame.Rect(self.rect.topleft + vector(-2, self.rect.height / 4), (2, self.rect.height / 2))

        collide_rects = [sprite.rect for sprite in self.collision_sprites]

        # Collisions
        self.on_surface["Floor"] = True if floor_rect.collidelist(collide_rects) >= 0 else False
        self.on_surface["Right"] = True if right_rect.collidelist(collide_rects) >= 0 else False
        self.on_surface["Left"] = True if left_rect.collidelist(collide_rects) >= 0 else False

        self.platform = None
        for sprite in [sprite for sprite in self.collision_sprites.sprites() if hasattr(sprite, "moving")]:
            if sprite.rect.colliderect(floor_rect):
                self.platform = sprite

    def collision(self, axis):
        for sprite in self.collision_sprites:
            if sprite.rect.colliderect(self.rect):
                if axis == "Horizontal":
                    #Left
                    if self.rect.left <= sprite.rect.right and self.old_rect.left >= sprite.old_rect.right:
                        self.rect.left = sprite.rect.right

                    #Right
                    if self.rect.right >= sprite.rect.left and self.old_rect.right <= sprite.old_rect.left:
                        self.rect.right = sprite.rect.left
                else:
                    # Top
                    if self.rect.top <= sprite.rect.bottom and self.old_rect.top >= sprite.old_rect.bottom:
                        self.rect.top = sprite.rect.bottom
                        if hasattr(sprite, "moving"):
                            self.rect.top += 6

                    # Bottom
                    if self.rect.bottom >= sprite.rect.top and self.old_rect.bottom <= sprite.old_rect.top:
                        self.rect.bottom = sprite.rect.top

                    self.direction.y = 0

    def update_timers(self):
        for timer in self.timers.values():
            timer.update()

    def update(self, dt):
        self.old_rect = self.rect.copy()
        self.update_timers()
        self.input()
        self.move(dt)
        self.platform_move(dt)
        self.check_contact()