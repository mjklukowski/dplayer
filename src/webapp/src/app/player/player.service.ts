import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PlayerStatus } from './model';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private status$ = new BehaviorSubject<PlayerStatus | null>(null)

  channelId?: string

  constructor(private http: HttpClient) { }

  switchChannel(channelId?: string) {
    this.channelId = channelId
    this.updateStatus(channelId);
  }

  getStatus(): Observable<PlayerStatus | null> {
    return this.status$.asObservable();
  }

  updateStatus(channelId?: string) {
    this.http.get<PlayerStatus>(`${environment.apiBaseURL}/player/${channelId}`)
      .pipe(tap(status => this.status$.next(status)))
      .subscribe()
  }

  play(trackId?: number) {
    if(trackId == null)
      return this.sendAction("PLAY")
    else
      return this.sendAction("PLAY", { trackId })
  }
  
  pause() {
    return this.sendAction("PAUSE")
  }
  
  resume() {
    return this.sendAction("RESUME")
  }
  
  stop() {
    return this.sendAction("STOP")
  }
  
  next() {
    return this.sendAction("NEXT")
  }
  
  prev() {
    return this.sendAction("PREV")
  }
  
  shuffleOn() {
    return this.sendAction("SHUFFLE_ON")
  }
  
  shuffleOff() {
    return this.sendAction("SHUFFLE_OFF")
  }
  
  private sendAction(action: string, args: { [key: string]: any } = {}) {
    return this.http.put<PlayerStatus>(`${environment.apiBaseURL}/player/${this.channelId}`, { action, ...args })
      .pipe(tap(status => this.status$.next(status)))
      .subscribe()
  }
}
