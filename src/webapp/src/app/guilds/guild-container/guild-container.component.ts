import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterEvent } from '@angular/router';
import { GuildService } from '../guild.service';
import { QueueComponent } from 'src/app/queue/queue/queue.component';
import { PlaylistComponent } from 'src/app/playlist/playlist/playlist.component';

@Component({
  selector: 'app-guild-container',
  templateUrl: './guild-container.component.html',
  styleUrls: ['./guild-container.component.scss']
})
export class GuildContainerComponent implements OnInit {

  guildId?: string | null

  activeTab: string = "queue"

  constructor(
    private route: ActivatedRoute,
    private guildService: GuildService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.guildId = params.get("guildId")
      const channelId = params.get("channelId")
      this.guildService.setActive(this.guildId, channelId)
    })
  }

  onActivate(component: Component) {
    if(component instanceof QueueComponent)
      this.activeTab = "queue"

    if(component instanceof PlaylistComponent)
      this.activeTab = "playlist"
  }

}
