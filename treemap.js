import React from 'react';

import Treemap from 'treemap';

import D3FlareData from '../datasets/d3-flare-example.json';
import ShowcaseButton from '../showcase-components/showcase-button';

const MODE = [
  'circlePack',
  'partition',
  'partition-pivot',
  'squarify',
  'resquarify',
  'slice',
  'dice',
  'slicedice',
  'binary'
];

const STYLES = {
  SVG: {
    stroke: '#ddd',
    strokeWidth: '0.25',
    strokeOpacity: 0.5
  },
  DOM: {
    border: 'thin solid #ddd'
  }
};

export default class SimpleTreemapExample extends React.Component {
  state = {
    modeIndex: 0,
    useSVG: true
  };

  updateModeIndex = increment => () => {
    const newIndex = this.state.modeIndex + (increment ? 1 : -1);
    const modeIndex =
      newIndex < 0 ? MODE.length - 1 : newIndex >= MODE.length ? 0 : newIndex;
    this.setState({modeIndex});
  };

  render() {
    const {modeIndex, useSVG} = this.state;

    return (
      <div className="centered-and-flexed">
        <div className="centered-and-flexed-controls">
          <ShowcaseButton
            onClick={() => this.setState({useSVG: !useSVG})}
            buttonContent={useSVG ? 'USE DOM' : 'USE SVG'}
          />
        </div>
        <div className="centered-and-flexed-controls">
          <ShowcaseButton
            onClick={this.updateModeIndex(false)}
            buttonContent={'PREV MODE'}
          />
          <div> {MODE[modeIndex]} </div>
          <ShowcaseButton
            onClick={this.updateModeIndex(true)}
            buttonContent={'NEXT MODE'}
          />
        </div>
        <Treemap
          {...{
            animation: true,
            className: 'nested-tree-example',
            colorType: 'literal',
            colorRange: ['#88572C'],
            data: D3FlareData,
            mode: MODE[modeIndex],
            renderMode: useSVG ? 'SVG' : 'DOM',
            height: 300,
            width: 350,
            margin: 10,
            getSize: d => d.value,
            getColor: d => d.hex,
            style: STYLES[useSVG ? 'SVG' : 'DOM']
          }}
        />
      </div>
    );
  }
}