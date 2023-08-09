import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Channel, Guild } from './model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GuildService {

  private activeGuildId$ = new BehaviorSubject<string | null>(null)
  private activeChannelId$ = new BehaviorSubject<string | null>(null)

  constructor(private http: HttpClient) { }

  getGuilds(): Observable<Guild[]> {
    return this.http.get<Guild[]>(`${environment.apiBaseURL}/guilds`)
  }

  getGuild(guildId: string): Observable<Guild> {
    return this.http.get<Guild>(`${environment.apiBaseURL}/guild/${guildId}`)
  }

  getChannels(guild: Guild): Observable<Channel[]> {
    return this.http.get<Channel[]>(`${environment.apiBaseURL}/guild/${guild.snowflake}/channels`)
  }

  setActive(guildId: string | null, channelId: string | null) {
    this.activeGuildId$.next(guildId);
    this.activeChannelId$.next(channelId);
  }

  getActiveGuildId(): Observable<string | null> {
    return this.activeGuildId$.asObservable();
  }

  getActiveChannelId(): Observable<string | null> {
    return this.activeChannelId$.asObservable();
  }
}
