import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-spotify',
  template: '<div id="spotify-player"></div>',
  styleUrls: ['./spotify.component.css']
})
export class SpotifyComponent implements OnInit {
  constructor(@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit() {
    // Verifica se il codice viene eseguito nel contesto del browser
    if (isPlatformBrowser(this.platformId)) {
      console.log('Esecuzione nel browser, window è disponibile.');
      this.initializeSpotifyPlayer();
    } else {
      console.log('Esecuzione su server: `window` non è disponibile.');
    }
  }

  // Inizializza il player Spotify solo se siamo nel browser
  initializeSpotifyPlayer() {
    (window as any).onSpotifyWebPlaybackSDKReady = () => {
      const token = 'YOUR_SPOTIFY_ACCESS_TOKEN'; // Sostituisci con il tuo access token
      const player = new (window as any).Spotify.Player({
        name: 'Angular Spotify Player',
        getOAuthToken: (cb: any) => { cb(token); },
        volume: 0.5
      });

      // Eventi del player
      player.addListener('ready', ({ device_id }: any) => {
        console.log('Player pronto con ID dispositivo:', device_id);
        this.transferPlaybackHere(token, device_id);
      });

      player.addListener('not_ready', ({ device_id }: any) => {
        console.log('Il dispositivo è offline:', device_id);
      });

      player.connect();
    };

    // Carica dinamicamente lo script di Spotify
    this.loadSpotifyScript();
  }

  // Carica lo script Spotify SDK
  loadSpotifyScript() {
    const script = document.createElement('script');
    script.id = 'spotify-player';
    script.src = 'https://sdk.scdn.co/spotify-player.js';
    document.body.appendChild(script);
  }

  transferPlaybackHere(token: string, deviceId: string) {
    fetch('https://api.spotify.com/v1/me/player', {
      method: 'PUT',
      body: JSON.stringify({
        "device_ids": [deviceId],
        "play": true
      }),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
    }).then(response => {
      if (response.ok) {
        console.log(`Riproduzione trasferita al dispositivo: ${deviceId}`);
      } else {
        console.error('Errore durante il trasferimento della riproduzione:', response.statusText);
      }
    });
  }

  playSong(token: string, deviceId: string, trackUri: string) {
    fetch(`https://api.spotify.com/v1/me/player/play?device_id=${deviceId}`, {
      method: 'PUT',
      body: JSON.stringify({
        uris: [trackUri]
      }),
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
    }).then(response => {
      if (response.ok) {
        console.log('Brano avviato con successo!');
      } else {
        console.error('Errore durante la riproduzione del brano:', response.statusText);
      }
    });
  }
}
