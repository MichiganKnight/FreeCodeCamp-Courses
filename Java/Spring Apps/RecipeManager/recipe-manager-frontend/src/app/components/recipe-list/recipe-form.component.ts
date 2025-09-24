import {Component} from '@angular/core';
import {RecipeService} from '../../services/recipe.service';

@Component({
  selector: 'app-recipe-form',
  template: `
    <h2>Add Recipe</h2>
    <input [(ngModel)]="name" placeholder="Recipe Name"/>
    <button (click)="addRecipe()">Add</button>
  `
})
export class RecipeFormComponent {
  name: string = "";

  constructor(private recipeService: RecipeService) {
  }

  addRecipe() {
    const newRecipe = {id: Date.now(), name: this.name};
    this.recipeService.addRecipe(newRecipe).subscribe(() => {
      alert("Recipe added!");
      this.name = "";
    });
  }
}
