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
}
