import React, {Component} from 'react';

export default class Click extends Component {

  
 constructor(){
  super();
  this.state={count:0};
 }
    
  handleClick = () => {
  this.setState(prevState => {
   return ({
    count: prevState.count + 1
   });
  });
 }

 render() {
  return (
   <button onClick={this.handleClick}>{this.state.count}</button>

  );
 }
 
}