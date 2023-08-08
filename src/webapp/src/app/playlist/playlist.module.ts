import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlaylistComponent } from './playlist/playlist.component';
import { PlaylistRoutingModule } from './playlist-routing.module';
import { PlaylistListComponent } from './playlist-list/playlist-list.component';
import { PlaylistTracksComponent } from './playlist-tracks/playlist-tracks.component';
import { TrackComponent } from '../common/track/track.component';



@NgModule({
  declarations: [
    PlaylistComponent,
    PlaylistListComponent,
    PlaylistTracksComponent,
    TrackComponent
  ],
  imports: [
    CommonModule,
    PlaylistRoutingModule
  ]
})
export class PlaylistModule { }
