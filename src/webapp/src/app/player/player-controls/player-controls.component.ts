import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { PlayerStatus } from '../model';
import { ActivatedRoute } from '@angular/router';
import { PlayerService } from '../player.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-player-controls',
  templateUrl: './player-controls.component.html',
  styleUrls: ['./player-controls.component.scss']
})
export class PlayerControlsComponent {
  
  status: PlayerStatus = PlayerStatus.STOPPED
  PlayerStatus = PlayerStatus

  channelId?: string;

  constructor(
    private route: ActivatedRoute,
    private playerService: PlayerService
  ) {}

  play() {
    if(this.status == PlayerStatus.STOPPED)
      this.playerService.play()
    else if(this.status == PlayerStatus.PAUSED)
      this.playerService.resume()
    
    this.status = PlayerStatus.PLAYING
  }

  pause() {
    this.status = PlayerStatus.PAUSED
    this.playerService.pause()
  }

  stop() {
    this.status = PlayerStatus.STOPPED
    this.playerService.stop()
  }

  prev() {
    this.playerService.prev()
  }

  next() {
    this.playerService.next()
  }

}
