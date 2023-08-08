import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Channel, Guild } from './model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GuildService {

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
}
