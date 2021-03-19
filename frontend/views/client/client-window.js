import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-tabs/src/vaadin-tabs.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@vaadin/vaadin-combo-box/src/vaadin-combo-box.js';
import '@vaadin/vaadin-tabs/src/vaadin-tab.js';

class ClientWindow extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    /*height: 100%;*/
                }
            </style>
<vaadin-horizontal-layout style="justify-content: space-between;">
 <div>
  <vaadin-horizontal-layout theme="spacing" style="flex-wrap: wrap;">
   <vaadin-tabs style="flex-shrink: 0; flex-grow: 0; align-self: center;" orientation="horizontal" selected="0">
    <vaadin-tab selected id="informationTab">
      Informácie 
    </vaadin-tab>
   </vaadin-tabs>
   <vaadin-combo-box id="chooseClient"></vaadin-combo-box>
  </vaadin-horizontal-layout>
 </div>
 <vaadin-button id="close">
   Zavrieť 
 </vaadin-button>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'client-window';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ClientWindow.is, ClientWindow);
