import { Injectable } from '@angular/core';
import { Guild } from '../guilds/model';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Track } from '../player/model';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QueueService {

  private tracks$ = new BehaviorSubject<Track[]>([])

  constructor(private http: HttpClient) { }

  getTracks(guild: Guild): Observable<Track[]> {
    this.http.get<Track[]>(`${environment.apiBaseURL}/guild/${guild.snowflake}/queue`)
      .pipe(tap(tracks => this.tracks$.next(tracks)))
      .subscribe()

    return this.tracks$.asObservable()
  }
}
