import {html, PolymerElement} from '@polymer/polymer/polymer-element.js';
import '@vaadin/vaadin-board/src/vaadin-board.js';
import '@vaadin/vaadin-board/src/vaadin-board-row.js';
import '../product/product-type-view.js';
import '../company/company-view.js';
import '../product/product-view.js';

class CompanyAndProductMainView extends PolymerElement {

    static get template() {
        return html`
<style include="shared-styles">
                :host {
                    display: block;
                    height: 100%;
                }
            </style>
<vaadin-board id="board">
 <vaadin-board-row>
  <product-view id="productView"></product-view>
 </vaadin-board-row>
 <vaadin-board-row>
  <company-view id="companyView"></company-view>
  <product-type-view id="productTypeView"></product-type-view>
 </vaadin-board-row>
</vaadin-board>
`;
    }

    static get is() {
        return 'company-and-product-main-view';
    }

    static get properties() {
        return {
            // Declare your properties here.
        };
    }
}

customElements.define(CompanyAndProductMainView.is, CompanyAndProductMainView);
