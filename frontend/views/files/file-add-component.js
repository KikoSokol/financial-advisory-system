import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-form-layout/src/vaadin-form-layout.js';
import '@vaadin/vaadin-upload/src/vaadin-upload.js';
import '@vaadin/vaadin-text-field/src/vaadin-text-field.js';
import '@vaadin/vaadin-button/src/vaadin-button.js';
import '@polymer/iron-icon/iron-icon.js';

class FileAddComponent extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-form-layout>
 <vaadin-upload id="upload" style="align-self: stretch;" colspan="2"></vaadin-upload>
 <vaadin-text-field label="Názov súboru" placeholder="Názov súboru" id="fileName" style="align-self: stretch;" colspan="2"></vaadin-text-field>
 <vaadin-button theme="primary" id="addFile" colspan="2">
  <iron-icon icon="lumo:plus"></iron-icon>Pridaj súbor
 </vaadin-button>
</vaadin-form-layout>
`;
    }

    static get is() {
        return 'file-add-component';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(FileAddComponent.is, FileAddComponent);
