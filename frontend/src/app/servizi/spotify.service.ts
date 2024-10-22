import { Inject, Injectable, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Injectable({
  providedIn: 'root',
})
export class SpotifyService {
  private IFrameAPI: any;

  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  // Carica lo script di Spotify
  loadSpotifyScript(): Promise<void> {
    return new Promise((resolve, reject) => {
      if (typeof document !== 'undefined' && !document.getElementById('spotify-iframe-api')) {
        const script = document.createElement('script');
        script.id = 'spotify-iframe-api';
        script.src = 'https://open.spotify.com/embed-podcast/iframe-api/v1';
        script.async = true;
        script.onload = () => {
          (window as any).onSpotifyIframeApiReady = (IFrameAPI: any) => {
            resolve(); // Risolvi la Promise quando lo script è caricato
          };
        };
        script.onerror = () => reject('Errore nel caricamento dello script di Spotify');
        document.body.appendChild(script);
      } else {
        // Se il documento non è disponibile o lo script è già caricato
        resolve();
      }
    });
  }

  // Crea il player Spotify all'interno dell'elemento con ID specificato
  createSpotifyPlayer(elementId: string, uri: string): void {
    this.loadSpotifyScript().then(() => {
      const element = document.getElementById(elementId);
      if (element && this.IFrameAPI) {
        const options = { uri };
        this.IFrameAPI.createController(element, options, (EmbedController: any) => {
          console.log("Spotify Player Created");
        });
      } else {
        console.error("IFrameAPI or element not found!");
      }
    });
  }
  
}
