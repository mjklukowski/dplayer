import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { SearchResult } from './model';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  constructor(private http: HttpClient) { }

  search(query: string): Observable<SearchResult[]> {
    return this.http.get<SearchResult[]>(`${environment.apiBaseURL}/youtube/search?query=${encodeURIComponent(query)}`)
      .pipe(
        map(results => results.map(r => {
          if(r.kind == "youtube#playlist")
            r.url = `https://www.youtube.com/playlist?list=${r.id}`
          else if(r.kind == "youtube#video")
            r.url = `https://youtu.be/${r.id}`
          return r
        }))
      )
  }
}
