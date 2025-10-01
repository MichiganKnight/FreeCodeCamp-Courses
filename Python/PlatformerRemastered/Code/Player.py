import pygame

from os.path import join

from Settings import *
from Timer import Timer

class Player(pygame.sprite.Sprite):
    def __init__(self, position, groups, collision_sprites, semi_collision_sprites):
        super().__init__(groups)

        self.image = pygame.image.load(join("Graphics", "Player", "Idle", "0.png"))

        # Rects
        self.rect = self.image.get_rect(topleft = position)
        self.hitbox_rect = self.rect.inflate(-76, -36)
        self.old_rect = self.hitbox_rect.copy()

        # Movement
        self.direction = vector()
        self.speed = 200
        self.gravity = 1300
        self.jump = False
        self.jump_height = 900

        # Collision
        self.collision_sprites = collision_sprites
        self.semi_collision_sprites = semi_collision_sprites
        self.on_surface = { "Floor": False, "Left": False, "Right": False }
        self.platform = None

        # Timer
        self.timers = {
            "Wall Jump": Timer(400),
            "Wall Slide Block": Timer(250),
            "Platform Skip": Timer(300)
        }

    def input(self):
        keys = pygame.key.get_pressed()
        input_vector = vector(0, 0)

        if not self.timers["Wall Jump"].active:
            if keys[pygame.K_d] or keys[pygame.K_RIGHT]:
                input_vector.x += 1
            elif keys[pygame.K_a] or keys[pygame.K_LEFT]:
                input_vector.x -= 1
            elif keys[pygame.K_s] or keys[pygame.K_DOWN]:
                self.timers["Platform Skip"].activate()
            self.direction.x = input_vector.normalize().x if input_vector else input_vector.x

        if keys[pygame.K_SPACE]:
            self.jump = True

    def move(self, dt):
        # Horizontal
        self.hitbox_rect.x += self.direction.x * self.speed * dt
        self.collision("Horizontal")

        # Vertical
        if not self.on_surface["Floor"] and any((self.on_surface["Left"], self.on_surface["Right"])) and not self.timers["Wall Slide Block"].active:
            self.direction.y = 0
            self.hitbox_rect.y += self.gravity / 10 * dt
        else:
            self.direction.y += self.gravity / 2 * dt
            self.hitbox_rect.y += self.direction.y * dt
            self.direction.y += self.gravity / 2 * dt

        # Jump
        if self.jump:
            if self.on_surface["Floor"]:
                self.direction.y = -self.jump_height
                self.timers["Wall Slide Block"].activate()
                self.hitbox_rect.bottom -= 1
            elif any((self.on_surface["Left"], self.on_surface["Right"])) and not self.timers["Wall Slide Block"].active:
                self.timers["Wall Jump"].activate()
                self.direction.y = -self.jump_height
                self.direction.x = 1 if self.on_surface["Left"] else -1
            self.jump = False

        self.collision("Vertical")
        self.semi_collision()
        self.rect.center = self.hitbox_rect.center

    def platform_move(self, dt):
        if self.platform:
            self.hitbox_rect.topleft += self.platform.direction * self.platform.speed * dt

    def check_contact(self):
        floor_rect = pygame.Rect(self.hitbox_rect.bottomleft, (self.hitbox_rect.width, 2))
        right_rect = pygame.Rect(self.hitbox_rect.topright + vector(0, self.hitbox_rect.height / 4), (2, self.hitbox_rect.height / 2))
        left_rect = pygame.Rect(self.hitbox_rect.topleft + vector(-2, self.hitbox_rect.height / 4), (2, self.hitbox_rect.height / 2))

        collide_rects = [sprite.rect for sprite in self.collision_sprites]
        semi_collide_rects = [sprite.rect for sprite in self.semi_collision_sprites]

        # Collisions
        self.on_surface["Floor"] = True if floor_rect.collidelist(collide_rects) >= 0 or floor_rect.collidelist(semi_collide_rects) >= 0 and self.direction.y >= 0 else False
        self.on_surface["Right"] = True if right_rect.collidelist(collide_rects) >= 0 else False
        self.on_surface["Left"] = True if left_rect.collidelist(collide_rects) >= 0 else False

        self.platform = None
        sprites = self.collision_sprites.sprites() + self.semi_collision_sprites.sprites()
        for sprite in [sprite for sprite in sprites if hasattr(sprite, "moving")]:
            if sprite.rect.colliderect(floor_rect):
                self.platform = sprite

    def collision(self, axis):
        for sprite in self.collision_sprites:
            if sprite.rect.colliderect(self.hitbox_rect):
                if axis == "Horizontal":
                    #Left
                    if self.hitbox_rect.left <= sprite.rect.right and int(self.old_rect.left) >= sprite.old_rect.right:
                        self.hitbox_rect.left = sprite.rect.right

                    #Right
                    if self.hitbox_rect.right >= sprite.rect.left and int(self.old_rect.right) <= sprite.old_rect.left:
                        self.hitbox_rect.right = sprite.rect.left
                else:
                    # Top
                    if self.hitbox_rect.top <= sprite.rect.bottom and int(self.old_rect.top) >= sprite.old_rect.bottom:
                        self.hitbox_rect.top = sprite.rect.bottom
                        if hasattr(sprite, "moving"):
                            self.hitbox_rect.top += 6

                    # Bottom
                    if self.hitbox_rect.bottom >= sprite.rect.top and int(self.old_rect.bottom) <= sprite.old_rect.top:
                        self.hitbox_rect.bottom = sprite.rect.top

                    self.direction.y = 0

    def semi_collision(self):
        if not self.timers["Platform Skip"].active:
            for sprite in self.semi_collision_sprites:
                if sprite.rect.colliderect(self.hitbox_rect):
                    if self.hitbox_rect.bottom >= sprite.rect.top and int(self.old_rect.bottom) <= sprite.old_rect.top:
                        self.hitbox_rect.bottom = sprite.rect.top
                        if self.direction.y > 0:
                            self.direction.y = 0

    def update_timers(self):
        for timer in self.timers.values():
            timer.update()

    def update(self, dt):
        self.old_rect = self.hitbox_rect.copy()
        self.update_timers()
        self.input()
        self.move(dt)
        self.platform_move(dt)
        self.check_contact()