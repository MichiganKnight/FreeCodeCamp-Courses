import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component'
import { RecipeListComponent } from './components/recipe-list/recipe-list.component';
import { RecipeFormComponent } from './components/recipe-list/recipe-form.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RecipeListComponent,
    RecipeFormComponent
  ],
  bootstrap: [AppComponent]
})

export class AppModule {}
