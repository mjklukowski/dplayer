import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
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
}
