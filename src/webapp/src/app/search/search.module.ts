import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AddTrackComponent } from './add-track/add-track.component';
import { SearchResultComponent } from './search-result/search-result.component';



@NgModule({
  declarations: [
    AddTrackComponent,
    SearchResultComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  exports: [
    AddTrackComponent
  ]
})
export class SearchModule { }
