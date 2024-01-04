import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FileSystemFileEntry } from 'ngx-file-drop';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) { }

  uploadVideo(fileEntry : File): Observable<any>{
    //http post call to uplaod the video

    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)

    return this.httpClient.post("http://localhost:8080/api/videos", formData);
    
  }
}
