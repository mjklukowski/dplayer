import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  channelId?: string

  constructor(private http: HttpClient) { }

  switchChannel(channelId?: string) {
    this.channelId = channelId
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
  
  private sendAction(action: string, args: { [key: string]: any } = {}) {
    return this.http.put(`${environment.apiBaseURL}/player/${this.channelId}`, { action, ...args }).subscribe()
  }
}
