import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Track } from '../player/model';
import { BehaviorSubject, Observable, catchError, map, pipe, switchMap, take, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QueueService {
  private tracks$ = new BehaviorSubject<Track[]>([])

  constructor(private http: HttpClient) { }

  getTracks(guildId: string): Observable<Track[]> {
    this.http.get<Track[]>(`${environment.apiBaseURL}/guild/${guildId}/queue`)
      .pipe(tap(tracks => this.tracks$.next(tracks)))
      .subscribe()

    return this.tracks$.asObservable()
  }

  addTrack(guildId: string | undefined, trackUrl: string) {
    this.tracks$.pipe(
      take(1),
      switchMap(tracks => this.http.post<Track[]>(`${environment.apiBaseURL}/guild/${guildId}/queue`, { url: trackUrl })
        .pipe(map(track => [...tracks, ...track])))
      )
      .subscribe(tracks => this.tracks$.next(tracks))
  }

  removeTrack(guildId: string | undefined, track: Track) {
    this.tracks$.pipe(
      take(1),
      map(tracks => {
        const index = tracks.indexOf(track)
        return {tracks, index}
      }),
      switchMap(({tracks, index}) => {
        return this.http.delete(`${environment.apiBaseURL}/guild/${guildId}/queue/${index}`)
          .pipe(tap(() => {
            tracks.splice(index, 1)
            this.tracks$.next(tracks)
          }))
      })
    )
    .subscribe()
  }

  clear(guildId: string | undefined) {
    return this.http.delete(`${environment.apiBaseURL}/guild/${guildId}/queue/clear`)
          .pipe(tap(() => this.tracks$.next([])))
  }
}
