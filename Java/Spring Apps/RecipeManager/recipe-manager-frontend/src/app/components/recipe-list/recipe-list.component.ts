import {Component, OnInit} from '@angular/core';
import {RecipeService} from '../../services/recipe.service';

@Component({
  selector: 'app-recipe-list',
  template: `
    <h2>Recipes</h2>
    <ul>
      <li *ngFor="let recipe of recipes">
        {{ recipe.name }} ({{ recipe.type }})
      </li>
    </ul>
  `
})

export class RecipeListComponent implements OnInit {
  recipes: any[] = [];

  constructor(private recipeService: RecipeService) {
  }

  ngOnInit() {
    this.recipeService.getRecipes().subscribe(data => this.recipes = data);
  }
}
