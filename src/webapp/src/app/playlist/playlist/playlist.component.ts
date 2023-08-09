import { Component, OnInit } from '@angular/core';
import { PlaylistService } from '../playlist.service';
import { ActivatedRoute } from '@angular/router';
import { Observable, map } from 'rxjs';
import { GuildService } from 'src/app/guilds/guild.service';
import { Playlist } from '../model';

@Component({
  selector: 'app-playlist',
  templateUrl: './playlist.component.html',
  styleUrls: ['./playlist.component.scss']
})
export class PlaylistComponent implements OnInit {

  playlists$?: Observable<Playlist[]>;
  activePlaylist?: Playlist;

  guildId?: string;

  constructor(
    private route: ActivatedRoute,
    private playlistService: PlaylistService
  ) {}

  ngOnInit(): void {
    this.route.paramMap
      .pipe(
        map(params => params.get("guildId")!),
      )
      .subscribe(guildId => {
        this.guildId = guildId;
        this.playlists$ = this.playlistService.getPlaylists(guildId)
      })
  }

  selectPlaylist(playlist: Playlist) {
    this.activePlaylist = playlist;
  }

  addPlaylist(name: string) {
    this.playlistService.addPlaylist(this.guildId, name);
  }

  removePlaylist(playlist: Playlist) {
    this.playlistService.removePlaylist(this.guildId, playlist);
  }

}
