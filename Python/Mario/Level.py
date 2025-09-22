from Particles import ParticleEffect
from Tiles import *
from Utils import *
from Enemy import Enemy
from Decoration import *
from Player import Player

class Level:
    def __init__(self, level_data, surface):

        # General Setup
        self.display_surface = surface
        self.world_shift = 0
        self.current_x = None

        # Player Setup
        player_layout = import_csv_layout(level_data['Player'])
        self.player = pygame.sprite.GroupSingle()
        self.goal = pygame.sprite.GroupSingle()
        self.player_setup(player_layout)

        # Dust Setup
        self.dust_sprite = pygame.sprite.GroupSingle()
        self.player_on_ground = False

        # Terrain Setup
        terrain_layout = import_csv_layout(level_data['Terrain'])
        self.terrain_sprites = self.create_tile_group(terrain_layout, 'Terrain')

        # Grass Setup
        grass_layout = import_csv_layout(level_data['Grass'])
        self.grass_sprites = self.create_tile_group(grass_layout, 'Grass')

        # Crates Setup
        crate_layout = import_csv_layout(level_data['Crates'])
        self.crate_sprites = self.create_tile_group(crate_layout, 'Crates')

        # Coins Setup
        coin_layout = import_csv_layout(level_data['Coins'])
        self.coin_sprites = self.create_tile_group(coin_layout, 'Coins')

        # Foreground Palms
        fg_palm_layout = import_csv_layout(level_data['FG Palms'])
        self.fg_palm_sprites = self.create_tile_group(fg_palm_layout, 'FG Palms')

        # Background Palms
        bg_palm_layout = import_csv_layout(level_data['BG Palms'])
        self.bg_palm_sprites = self.create_tile_group(bg_palm_layout, 'BG Palms')

        # Enemies Setup
        enemy_layout = import_csv_layout(level_data['Enemies'])
        self.enemy_sprites = self.create_tile_group(enemy_layout, 'Enemies')

        # Constraints Setup
        constraint_layout = import_csv_layout(level_data['Constraints'])
        self.constraint_sprites = self.create_tile_group(constraint_layout, 'Constraints')

        # Decorations Setup
        self.sky = Sky(8)
        level_width = len(terrain_layout[0]) * tile_size
        self.water = Water(screen_height - 20, level_width)
        self.clouds = Clouds(400, level_width, 25)

    def player_setup(self, layout):
        for row_index, row in enumerate(layout):
            for col_index, val in enumerate(row):
                x = col_index * tile_size
                y = row_index * tile_size

                if val == '0':
                    sprite = Player((x, y), self.display_surface, self.create_jump_particles)
                    self.player.add(sprite)
                elif val == '1':
                    hat_surface = pygame.image.load(join('Assets', 'Character', 'hat.png')).convert_alpha()
                    sprite = StaticTile(tile_size, x, y, hat_surface)

                    self.goal.add(sprite)

    def horizontal_movement_collision(self):
        player = self.player.sprite
        player.rect.x += player.direction.x * player.speed
        collision_sprites = self.terrain_sprites.sprites() + self.crate_sprites.sprites() + self.fg_palm_sprites.sprites()

        for sprite in collision_sprites:
            if sprite.rect.colliderect(player.rect):
                if player.direction.x < 0:
                    player.rect.left = sprite.rect.right
                    player.on_left = True
                    self.current_x = sprite.rect.left
                elif player.direction.x > 0:
                    player.rect.right = sprite.rect.left
                    player.on_right = True
                    self.current_x = sprite.rect.right

        if player.on_left and (player.rect.left < self.current_x or player.direction.x >= 0):
            player.on_left = False

        if player.on_right and (player.rect.right > self.current_x or player.direction.x <= 0):
            player.on_right = False

    def vertical_movement_collision(self):
        player = self.player.sprite
        player.apply_gravity()
        collision_sprites = self.terrain_sprites.sprites() + self.crate_sprites.sprites() + self.fg_palm_sprites.sprites()

        for sprite in collision_sprites:
            if sprite.rect.colliderect(player.rect):
                if player.direction.y > 0:
                    player.rect.bottom = sprite.rect.top
                    player.direction.y = 0
                    player.on_ground = True
                elif player.direction.y < 0:
                    player.rect.top = sprite.rect.bottom
                    player.direction.y = 0
                    player.on_ceiling = True

        if player.on_ground and player.direction.y < 0 or player.direction.y > 1:
            player.on_ground = False

        if player.on_ceiling and player.direction.y > 0:
            player.on_ceiling = False

    def scroll_x(self):
        player = self.player.sprite
        player_x = player.rect.centerx
        direction_x = player.direction.x

        if player_x < screen_width / 4 and direction_x < 0:
            self.world_shift = 8
            player.speed = 0
        elif player_x > screen_width - (screen_width / 4) and direction_x > 0:
            self.world_shift = -8
            player.speed = 0
        else:
            self.world_shift = 0
            player.speed = 8

    def create_jump_particles(self, pos):
        if self.player.sprite.facing_right:
            pos -= pygame.math.Vector2(10, 5)
        else:
            pos += pygame.math.Vector2(10, 5)
        jump_particle_sprite = ParticleEffect(pos, 'Jump')
        self.dust_sprite.add(jump_particle_sprite)

    def get_player_on_ground(self):
        if self.player.sprite.on_ground:
            self.player_on_ground = True
        else:
            self.player_on_ground = False

    def create_landing_dust(self):
        if not self.player_on_ground and self.player.sprite.on_ground and not self.dust_sprite.sprites():
            if self.player.sprite.facing_right:
                offset = pygame.math.Vector2(10, 15)
            else:
                offset = pygame.math.Vector2(-10, 15)
            fall_dust_particle = ParticleEffect(self.player.sprite.rect.midbottom - offset, 'Land')
            self.dust_sprite.add(fall_dust_particle)

    def create_tile_group(self, layout, group_type):
        sprite_group = pygame.sprite.Group()

        for row_index, row in enumerate(layout):
            for col_index, val in enumerate(row):
                if val != '-1':
                    x = col_index * tile_size
                    y = row_index * tile_size

                    if group_type == 'Terrain':
                        terrain_tile_list = import_cut_graphics(join('Assets', 'Terrain', 'terrain_tiles.png'))
                        tile_surface = terrain_tile_list[int(val)]

                        sprite = StaticTile(tile_size, x, y, tile_surface)
                    elif group_type == 'Grass':
                        grass_tile_list = import_cut_graphics(join('Assets', 'Decoration', 'Grass', 'grass.png'))
                        tile_surface = grass_tile_list[int(val)]

                        sprite = StaticTile(tile_size, x, y, tile_surface)
                    elif group_type == 'Crates':
                        sprite = Crate(tile_size, x, y)
                    elif group_type == 'Coins':
                        if val == '0':
                            sprite = Coin(tile_size, x, y, join('Assets', 'Coins', 'Gold'))
                        elif val == '1':
                            sprite = Coin(tile_size, x, y, join('Assets', 'Coins', 'Silver'))
                    elif group_type == 'FG Palms':
                        if val == '0':
                            sprite = Palm(tile_size, x, y, join('Assets', 'Terrain', 'Palm_Small'), 38)
                        elif val == '1':
                            sprite = Palm(tile_size, x, y, join('Assets', 'Terrain', 'Palm_Large'), 70)
                    elif group_type == 'BG Palms':
                        sprite = Palm(tile_size, x, y, join('Assets', 'Terrain', 'Palm_BG'), 64)
                    elif group_type == 'Enemies':
                        sprite = Enemy(tile_size, x, y)
                    elif group_type == 'Constraints':
                        sprite = Tile(tile_size, x, y)

                    sprite_group.add(sprite)

        return sprite_group

    def enemy_collision_reverse(self):
        for enemy in self.enemy_sprites.sprites():
            if pygame.sprite.spritecollide(enemy, self.constraint_sprites, False):
                enemy.reverse()

    def run(self):
        # Run Entire Game

        # Sky
        self.sky.draw(self.display_surface)
        self.clouds.draw(self.display_surface, self.world_shift)

        # Background Palms
        self.bg_palm_sprites.update(self.world_shift)
        self.bg_palm_sprites.draw(self.display_surface)

        # Terrain
        self.terrain_sprites.update(self.world_shift)
        self.terrain_sprites.draw(self.display_surface)

        # Enemies
        self.enemy_sprites.update(self.world_shift)
        self.constraint_sprites.update(self.world_shift)
        self.enemy_collision_reverse()
        self.enemy_sprites.draw(self.display_surface)

        # Crates
        self.crate_sprites.update(self.world_shift)
        self.crate_sprites.draw(self.display_surface)

        # Grass
        self.grass_sprites.update(self.world_shift)
        self.grass_sprites.draw(self.display_surface)

        # Coins
        self.coin_sprites.update(self.world_shift)
        self.coin_sprites.draw(self.display_surface)

        # Foreground Palms
        self.fg_palm_sprites.update(self.world_shift)
        self.fg_palm_sprites.draw(self.display_surface)

        # Dust Sprites
        self.dust_sprite.update(self.world_shift)
        self.dust_sprite.draw(self.display_surface)

        # Player Sprites
        self.player.update()

        self.horizontal_movement_collision()
        self.get_player_on_ground()
        self.vertical_movement_collision()
        self.create_landing_dust()
        self.scroll_x()

        self.player.draw(self.display_surface)
        self.goal.update(self.world_shift)
        self.goal.draw(self.display_surface)

        # Water
        self.water.draw(self.display_surface, self.world_shift)