import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlayerControlsComponent } from './player-controls/player-controls.component';



@NgModule({
  declarations: [
    PlayerControlsComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PlayerControlsComponent
  ]
})
export class PlayerModule { }
