import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, map, switchMap, take, tap } from 'rxjs';
import { Guild } from '../guilds/model';
import { Playlist } from './model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  private playlists$ = new BehaviorSubject<Playlist[]>([]);

  constructor(private http: HttpClient) { }

  getPlaylists(guildId: string): Observable<Playlist[]> {
    this.http.get<Playlist[]>(`${environment.apiBaseURL}/guild/${guildId}/playlist`)
    .pipe(tap(playlists => this.playlists$.next(playlists)))  
    .subscribe()

    return this.playlists$.asObservable();
  }

  addPlaylist(guildId: string | undefined, name: string) {
    this.playlists$.pipe(
      take(1),
      switchMap(playlists => this.http.post<Playlist>(`${environment.apiBaseURL}/guild/${guildId}/playlist`, { name })
        .pipe(
          map(playlist => [...playlists, playlist])
        )
      )
    ).subscribe(playlists => this.playlists$.next(playlists))
  }

  removePlaylist(guildId: string | undefined, playlist: Playlist) {
    this.playlists$.pipe(
      take(1),
      map(playlists => {
        const index = playlists.indexOf(playlist)
        return {playlists, index}
      }),
      switchMap(({playlists, index}) => {
        return this.http.delete(`${environment.apiBaseURL}/guild/${guildId}/playlist/${index}`)
          .pipe(tap(() => {
            playlists.splice(index, 1)
            this.playlists$.next(playlists)
          }))
      })
    )
    .subscribe()
  }
}
