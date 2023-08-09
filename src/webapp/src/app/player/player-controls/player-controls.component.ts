import { Component, OnInit } from '@angular/core';
import { PlayerState, PlayerStatus } from '../model';
import { PlayerService } from '../player.service';

@Component({
  selector: 'app-player-controls',
  templateUrl: './player-controls.component.html',
  styleUrls: ['./player-controls.component.scss']
})
export class PlayerControlsComponent implements OnInit {
  
  status: PlayerStatus | null = {
    channel: null,
    currentTrack: null,
    guild: null,
    shuffle: false,
    state: PlayerState.STOPPED
  }
  PlayerState = PlayerState

  constructor(
    private playerService: PlayerService
  ) {}

  ngOnInit(): void {
    this.playerService.getStatus().subscribe(status => this.status = status)
  }

  play() {
    if(this.status == null)
      return;

    if(this.status.state == PlayerState.STOPPED)
      this.playerService.play()
    else if(this.status.state == PlayerState.PAUSED)
      this.playerService.resume()
  }

  pause() {
    this.playerService.pause()
  }

  stop() {
    this.playerService.stop()
  }

  prev() {
    this.playerService.prev()
  }

  next() {
    this.playerService.next()
  }

  toggleShuffle() {
    if(this.status == null)
      return;
    
    if(this.status.shuffle)
      this.playerService.shuffleOff();
    else
      this.playerService.shuffleOn();
  }

}
