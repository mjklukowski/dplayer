import { Component, OnInit } from '@angular/core';
import { QueueService } from '../queue.service';
import { Observable, map, switchMap } from 'rxjs';
import { Track } from 'src/app/player/model';
import { ActivatedRoute } from '@angular/router';
import { GuildService } from 'src/app/guilds/guild.service';

@Component({
  selector: 'app-queue',
  templateUrl: './queue.component.html',
  styleUrls: ['./queue.component.scss']
})
export class QueueComponent implements OnInit {

  tracks$?: Observable<Track[]>
  guildId?: string

  constructor(
    private route: ActivatedRoute,
    private queueService: QueueService,
    private guildService: GuildService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        map(params => params.get("guildId")!)
      )
      .subscribe(guildId => {
        this.guildId = guildId;
        this.tracks$ = this.queueService.getTracks(guildId);
      })
  }

  removeTrack(track: Track) {
    this.queueService.removeTrack(this.guildId, track);
  }

}
