import { Component, OnDestroy, OnInit } from '@angular/core';
import { QueueService } from '../queue.service';
import { Observable, Subscription, map, of, switchMap, take } from 'rxjs';
import { PlayerState, Track } from 'src/app/player/model';
import { ActivatedRoute } from '@angular/router';
import { GuildService } from 'src/app/guilds/guild.service';
import { PlayerService } from 'src/app/player/player.service';

@Component({
  selector: 'app-queue',
  templateUrl: './queue.component.html',
  styleUrls: ['./queue.component.scss']
})
export class QueueComponent implements OnInit {

  tracks$?: Observable<Track[]>
  guildId?: string
  channelId?: string

  currentTrackId$?: Observable<number | undefined>

  constructor(
    private route: ActivatedRoute,
    private queueService: QueueService,
    private playerService: PlayerService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        map(params => ({
          guildId: params.get("guildId")!,
          channelId: params.get("channelId")!
        }))
      )
      .subscribe(({guildId, channelId}) => {
        this.guildId = guildId;
        this.playerService.switchChannel(channelId);
        this.tracks$ = this.queueService.getTracks(guildId);
      })

    this.currentTrackId$ = this.playerService.getStatus()
      .pipe(map(status => {
        if(status?.state == PlayerState.STOPPED)
          return;
        return status?.currentTrackId
      }))
  }

  removeTrack(track: Track) {
    this.queueService.removeTrack(this.guildId, track);
  }

  addTrack(trackUrl: string) {
    this.queueService.addTrack(this.guildId, trackUrl);
  }

  play(track: Track) {
    this.tracks$?.pipe(
      take(1),
      map(tracks => tracks.findIndex(t => t == track))
    ).subscribe(index => this.playerService.play(index))
  }

}
