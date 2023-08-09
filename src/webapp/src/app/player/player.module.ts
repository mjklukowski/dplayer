import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlayerControlsComponent } from './player-controls/player-controls.component';
import { NowPlayingComponent } from './now-playing/now-playing.component';



@NgModule({
  declarations: [
    PlayerControlsComponent,
    NowPlayingComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PlayerControlsComponent
  ]
})
export class PlayerModule { }
