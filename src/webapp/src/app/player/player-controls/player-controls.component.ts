import { Component, OnDestroy, OnInit } from '@angular/core';
import { PlayerState, PlayerStatus } from '../model';
import { PlayerService } from '../player.service';
import { Subscription, timer } from 'rxjs';

@Component({
  selector: 'app-player-controls',
  templateUrl: './player-controls.component.html',
  styleUrls: ['./player-controls.component.scss']
})
export class PlayerControlsComponent implements OnInit, OnDestroy {
  
  status: PlayerStatus | null = {
    channel: null,
    currentTrack: null,
    guild: null,
    shuffle: false,
    state: PlayerState.STOPPED
  }
  PlayerState = PlayerState

  private pollingSub!: Subscription;

  constructor(
    private playerService: PlayerService
  ) {}

  ngOnInit(): void {
    this.playerService.getStatus().subscribe(status => this.status = status)
    this.pollingSub = timer(0, 10000).subscribe(() => {
      if(this.status != null && this.status.channel != null)
        this.playerService.updateStatus(this.status.channel.snowflake)
    });
  }

  ngOnDestroy(): void {
    this.pollingSub.unsubscribe();
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
