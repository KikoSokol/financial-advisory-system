import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-vertical-layout.js';

class PhysicalPersonCard extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-horizontal-layout>
 <vaadin-vertical-layout style="width: 100%; flex-shrink: 0;">
  <h4 id="fullName" style="margin-left: var(--lumo-space-s); margin-bottom: 0; margin-top: var(--lumo-space-xs);">Heading 4</h4>
  <vaadin-horizontal-layout theme="spacing" style="margin-left: var(--lumo-space-l);">
   <label>Email:</label>
   <label id="email">Label</label>
  </vaadin-horizontal-layout>
  <vaadin-horizontal-layout theme="spacing" style="margin-left: var(--lumo-space-l);">
   <label>Telefón:</label>
   <label id="phone">Label</label>
  </vaadin-horizontal-layout>
 </vaadin-vertical-layout>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'physical-person-card';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(PhysicalPersonCard.is, PhysicalPersonCard);
