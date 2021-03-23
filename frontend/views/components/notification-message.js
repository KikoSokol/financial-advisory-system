import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';

class NotificationMessage extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-horizontal-layout style="width: 100%; align-items: center; justify-content: center;">
 <div id="iconPlace" style="width: 10%; flex-grow: 1; flex-shrink: 1; align-self: stretch;">
   Div 
 </div>
 <vaadin-horizontal-layout id="messagesPlace" style="flex-grow: 1; width: 90%; height: 100%;">
  <div id="messageText" style="flex-grow: 1; height: 100%; align-self: center;">
    Div 
  </div>
 </vaadin-horizontal-layout>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'notification-message';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(NotificationMessage.is, NotificationMessage);
