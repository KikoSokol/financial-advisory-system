import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-select/src/vaadin-select.js';
import '@vaadin/vaadin-ordered-layout/src/vaadin-horizontal-layout.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class ProductEditor extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout>
 <vaadin-text-field label="Názov produktu" placeholder="Názov produktu" id="name" required></vaadin-text-field>
 <vaadin-select value="Item one" id="productType" label="Typ produktu" required placeholder="Typ produktu"></vaadin-select>
 <vaadin-select value="Item one" id="company" label="Spoločnosť" required placeholder="Spoločnosť"></vaadin-select>
</vaadin-form-layout>
<vaadin-horizontal-layout style="justify-content: flex-end;">
 <vaadin-button theme="primary error" id="delete" style="margin-right: var(--lumo-space-m);">
  VYMAZAŤ
 </vaadin-button>
 <vaadin-button theme="primary" id="cancel">
   ZRUŠIŤ 
 </vaadin-button>
 <vaadin-button theme="primary success" id="save" style="margin-right: var(--lumo-space-m); margin-left: var(--lumo-space-m);">
  <iron-icon icon="lumo:checkmark"></iron-icon>ULOŽIŤ 
 </vaadin-button>
</vaadin-horizontal-layout>
`;
    }

    static get is() {
        return 'product-editor';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(ProductEditor.is, ProductEditor);
