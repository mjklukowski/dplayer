import { Component } from '@angular/core';
import { PlayerStatus } from '../model';

@Component({
  selector: 'app-player-controls',
  templateUrl: './player-controls.component.html',
  styleUrls: ['./player-controls.component.scss']
})
export class PlayerControlsComponent {
  
  status: PlayerStatus = PlayerStatus.STOPPED
  PlayerStatus = PlayerStatus

  play() {
    this.status = PlayerStatus.PLAYING
  }

  pause() {
    this.status = PlayerStatus.PAUSED
  }

  stop() {
    this.status = PlayerStatus.STOPPED
  }

  prev() {

  }

  next() {
    
  }

}
