"use strict";
//기능 추가
//전역 변수
let inFile;
let inImage, inH, inW;

let outCanvas, outCtx, outPaper;
let outImage, outH, outW;
let saveImage, saveH, saveW;

let mood;

let buttonRange = document.querySelector(".button-range");
let openFile = document.querySelector(".openFile");
let equal = document.querySelector(".equal");
let mirror_lr = document.querySelector(".mirror_lr");
let mirror_tb = document.querySelector(".mirror_tb");
let rotate90 = document.querySelector(".rotate90");
let histoSt = document.querySelector(".histoSt");
let embos = document.querySelector(".embos");
let endIn = document.querySelector(".endIn");

let brightness = document.querySelector(".brightness");
let blur = document.querySelector(".blur");
let grayscale = document.querySelector(".grayscale");
let reverse = document.querySelector(".reverse");
let black_white = document.querySelector(".black_white");

let colorbox = document.querySelectorAll(".colorlist .colorbox");
const hoveredColor = document.querySelector("#hovered-color");
const selectedColor = document.querySelector("#selected-color");
let onCanvas = document.querySelector(".display-canvas");

function init() {
  let displayButton = document.querySelector(".display-button");
  if (displayButton != null) {
    // 초기화면에서 이미지를 첨부하는 라벨이미지를 사진으로 대체하도록
    outCanvas = document.createElement("canvas");
    outCanvas.setAttribute("id", "outCanvas");
    outCtx = outCanvas.getContext("2d");
    document
      .querySelector(".display-canvas")
      .replaceChild(outCanvas, displayButton);
  } else {
    console.log("reload");
    reload();
  }
}
function reload() {
  outCanvas = document.querySelector("#outCanvas");
  outCtx = outCanvas.getContext("2d");
  document.querySelector(".colorNames").innerHTML = ` `;
}

//함수//
function openImage() {
  inFile = document.querySelector(".openFile").files[0];
  //그림 파일 >> 이미지 객체로 불러오기
  inImage = new Image(); //빈 이미지 객체 생성
  console.log(inFile);
  if (inFile == undefined) {
    console.log("image undefined");
    displayImage();
  }
  inImage.src = `images/${inFile.name}`;

  inImage.onload = function () {
    //입력 파일의 크기를 알아냄

    console.log(inImage.width);
    inW = inImage.width;
    inH = inImage.height;

    // 캔버스 크기를 결정
    outCanvas.width = inW;
    outCanvas.height = inH;
    outCtx.drawImage(inImage, 0, 0, inW, inH);

    // 입력 3차원 배열을 준비
    inImage = new Array(3); // 3장짜리 배열 (r, g, b)
    for (let i = 0; i < 3; i++) {
      //1.총 3장만들기 위해서 3번 반복
      inImage[i] = new Array(inH); //2. 1장마다 2차원 배열 생성
      for (let k = 0; k < inH; k++) {
        inImage[i][k] = new Array(inW);
      }
    }

    //출력된 캔버스에서 픽셀값 뽑아내기
    let imageData = outCtx.getImageData(0, 0, inW, inH);
    let R, G, B;
    for (let i = 0; i < inH; i++) {
      for (let k = 0; k < inW; k++) {
        let px = (i * inW + k) * 4; //1픽셀 = 4byte(r,g,b,alpha값까지 포함) //순서는 0,4,8~~
        R = imageData.data[px + 0];
        G = imageData.data[px + 1];
        B = imageData.data[px + 2];

        // Alpha = imageData.data[px+3]; //알파값(투명도)까지 한다면 추가하기

        inImage[0][i][k] = String.fromCharCode(R).charCodeAt(0);
        inImage[1][i][k] = String.fromCharCode(G).charCodeAt(0);
        inImage[2][i][k] = String.fromCharCode(B).charCodeAt(0);
        // inImage[3][i][k] = String.fromCharCode(A);
      }
    }
    saveImage = undefined;
    moodImage();

    onCanvas.addEventListener("mousemove", (e) => {
      hoveredColor.style.background = pick(e);
      console.log("mousemoving");
    });
    onCanvas.addEventListener("click", function (e) {
      console.log("mousemclicked");

      selectedColor.style.background = pick(e);
    });
  };
}

//이미지 색상 테마 추출
function moodImage() {
  let r, g, b;
  let histo = new Array(inH);
  let color = 0;

  for (let i = 0; i < inH; i++) {
    histo[i] = new Array(inW);
  }

  for (let i = 0; i < inH; i++) {
    for (let k = 0; k < inW; k++) {
      r = inImage[0][i][k];
      g = inImage[1][i][k];
      b = inImage[2][i][k];
      color = ConvertRGBtoHex(r, g, b);

      histo[i][k] = color;
    }
  }
  //2차원->1차원 배열로
  const histoArr = histo.reduce(function (acc, cur) {
    return acc.concat(cur);
  });

  //Map으로 중복 개수 알아보기
  const histoMap = new Map();
  histoArr.forEach((x) => {
    histoMap[x] = (histoMap[x] || 0) + 1;
    histoMap.set(x, histoMap[x]);
  });

  const histoSort = new Map(
    [...histoMap.entries()].sort((a, b) => b[1] - a[1])
  );

  let maincolors = Array.from(histoSort.keys());

  console.log(maincolors.slice(0, 5));
  maincolors = maincolors.slice(0, 5);

  for (let i = 0; i < 5; i++) {
    colorbox[i].style.backgroundColor = maincolors[i];
    let colorName = document.createElement("p");
    colorName.setAttribute("id", "colorName");
    document.querySelector(".colorNames").appendChild(colorName);
    colorName.innerHTML = maincolors[i];
  }
}
function ConvertRGBtoHex(red, green, blue) {
  return "#" + ColorToHex(red) + ColorToHex(green) + ColorToHex(blue);
}
function ColorToHex(color) {
  let hexadecimal = color.toString(16);
  return hexadecimal.length == 1 ? "0" + hexadecimal : hexadecimal;
}

// 캔버스에 출력
function displayImage() {
  // 캔버스 크기를 결정
  outCanvas.width = saveImage ? saveW :outW;
  outCanvas.height =saveImage ? saveH : outH;
  console.log("saveH,saveW: "+saveH,saveW);
  console.log("outH,outW: "+outH,outW);

  let r, g, b;
  outPaper = outCtx.createImageData(outW, outH); //종이 붙임

  for (let i = 0; i < outH; i++) {
    for (let k = 0; k < outW; k++) {
      r = saveImage ? saveImage[0][i][k]: outImage[0][i][k];
      g = saveImage ? saveImage[1][i][k]: outImage[1][i][k];
      b = saveImage ? saveImage[2][i][k]: outImage[2][i][k];
      outPaper.data[(i * outW + k) * 4 + 0] = r;
      outPaper.data[(i * outW + k) * 4 + 1] = g;
      outPaper.data[(i * outW + k) * 4 + 2] = b;
      outPaper.data[(i * outW + k) * 4 + 3] = 255; // Alpha: 투명도 0%로 하기 위해서 255써야함
    }
  }

  outCtx.putImageData(outPaper, 0, 0);
}

function save_Image(Image, H, W) {
  saveImage = Image.map((v) => v.slice());
  saveH = H;
  saveW = W;
  console.log("save Image");
  console.log("Inimage"+inImage[2][40][100]);
  console.log("saveimage"+saveImage[2][40][100]);
}

//기능

function equal_Image() {
  outH = inH;
  outW = inW;
  //입력받은 이미지의 값 복사
  outImage = inImage.map((v) => v.slice());

  //이미지 저장
  save_Image(outImage, outH, outW);
  displayImage();
}

function mirrorLR_image() {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.

  outH =  inH;
  outW = inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }

  ///진짜 영상 알고리즘 부분
  for (let rgb = 0; rgb < 3; rgb++) {
    for (let i = 0; i < inH; i++) {
      for (let k = 0; k < inW; k++) {
        outImage[rgb][i][inW - 1 - k] = saveImage
          ? saveImage[rgb][i][k]
          : inImage[rgb][i][k];
      }
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}

//상하 반전
function mirrorTB_image() {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH = saveImage ? saveH : inH;
  outW = saveImage ? saveW : inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }

  ///진짜 영상 알고리즘 부분
  for (let rgb = 0; rgb < 3; rgb++) {
    for (let i = 0; i < inH; i++) {
      for (let k = 0; k < inW; k++) {
        outImage[rgb][inH - 1 - i][k] = saveImage
          ? saveImage[rgb][i][k]
          : inImage[rgb][i][k];
      }
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}

//90도 회전
function rotate90_image() {
  if (saveImage!=null){
    console.log("save Image exist");

  }

  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH = saveImage ? saveW : inW;
  outW = saveImage ? saveH : inH;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }
  for (let rgb = 0; rgb < 3; rgb++) {
    for (let i = 0; i < inH; i++) {
      for (let k = 0; k < inW; k++) {
        outImage[rgb][k][inH - 1 - i] = saveImage
          ? saveImage[rgb][i][k]
          : inImage[rgb][i][k];
      }
    }
  }

  save_Image(outImage, outH, outW);
  displayImage();
}

function brightnessImage(v) {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH = saveImage ? saveH : inH;
  outW = saveImage ? saveW : inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }

  ///진짜 영상 알고리즘 부분
  let value = parseInt(v);
  // console.log(value);
  if (saveImage != null) { //그전에 실행된 기능이 있으면
    console.log("save->brightness");
    for (let rgb = 0; rgb < 3; rgb++) {
      for (let i = 0; i < inH; i++) {
        for (let k = 0; k < inW; k++) {
          if (saveImage[rgb][i][k] + value > 255) {
            outImage[rgb][i][k] = 255;
          } else if (saveImage[rgb][i][k] + value < 0) {
            outImage[rgb][i][k] = 0;
          } else {
            outImage[rgb][i][k] = saveImage[rgb][i][k] + value;
          }
        }
      }
    }
   
  } else {
    for (let rgb = 0; rgb < 3; rgb++) {
      for (let i = 0; i < inH; i++) {
        for (let k = 0; k < inW; k++) {
          if (inImage[rgb][i][k] + value > 255) {
            outImage[rgb][i][k] = 255;
          } else if (inImage[rgb][i][k] + value < 0) {
            outImage[rgb][i][k] = 0;
          } else {
            outImage[rgb][i][k] = inImage[rgb][i][k] + value;
          }
        }
      }
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}

//블러

function blur_Image() {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH = saveImage ? saveH : inH;
  outW =  saveImage ? saveW :inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }

  ///진짜 영상 알고리즘 부분
  let mask = [
    [1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0],
    [1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0],
    [1.0 / 9.0, 1.0 / 9.0, 1.0 / 9.0],
  ];

  // 임시 입력 배열 (입력  배열  +2 )
  let tmpInput = new Array(3);

  for (let i = 0; i < 3; i++) {
    tmpInput[i] = new Array(outH + 2);
    for (let k = 0; k < outH + 2; k++) {
      tmpInput[i][k] = new Array(outW + 2);
    }
  }
  //임시 입력 배열 초기화(127로)
  for (let i = 0; i < inH + 2; i++) {
    for (let k = 0; k < inW + 2; k++) {
      tmpInput[0][i][k] = 127.0;
      tmpInput[1][i][k] = 127.0;
      tmpInput[2][i][k] = 127.0;
    }
  }

  console.log(tmpInput);
  //원입력->임시입력
  for (let rgb = 0; rgb < 3; rgb++) {
    for (let i = 0; i < inH; i++) {
      for (let k = 0; k < inW; k++) {
        tmpInput[rgb][i + 1][k + 1] =  saveImage ? parseFloat(saveImage[rgb][i][k]) :parseFloat(inImage[rgb][i][k]);
      }
    }
  }
  //임시 출력 배열 (출력배열 크기 동일)
  let tmpOutput = new Array(3);
  for (let i = 0; i < 3; i++) {
    tmpOutput[i] = new Array(outH);
    for (let k = 0; k < outH; k++) {
      tmpOutput[i][k] = new Array(outW);
    }
  }

  //회선 연산
  for (let i = 0; i < inH; i++) {
    for (let k = 0; k < inW; k++) {
      //한 점에 대해서 처리
      let s1 = 0.0,
        s2 = 0.0,
        s3 = 0.0;
      for (let m = 0; m < 3; m++) {
        for (let n = 0; n < 3; n++) {
          s1 += tmpInput[0][i + m][k + n] * mask[m][n];
          s2 += tmpInput[1][i + m][k + n] * mask[m][n];
          s3 += tmpInput[2][i + m][k + n] * mask[m][n];
        }
      }
      tmpOutput[0][i][k] = s1;
      tmpOutput[1][i][k] = s2;
      tmpOutput[2][i][k] = s3;
    }
  }

  //임시 출력->원 출력
  for (let i = 0; i < outH; i++) {
    for (let k = 0; k < outW; k++) {
      outImage[0][i][k] = parseInt(tmpOutput[0][i][k]);
      outImage[1][i][k] = parseInt(tmpOutput[1][i][k]);
      outImage[2][i][k] = parseInt(tmpOutput[2][i][k]);
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}

function embosImage() {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH = saveImage ? saveH : inH;
  outW =  saveImage ? saveW :inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }

  ///진짜 영상 알고리즘 부분
  let mask =[
    [-1.0,0.0,0.0],
    [0.0,0.0,0.0],
    [0.0,0.0,1.0]];
  // 임시 입력 배열 (입력  배열  +2 )
  let tmpInput = new Array(3);

  for (let i = 0; i < 3; i++) {
    tmpInput[i] = new Array(outH + 2);
    for (let k = 0; k < outH + 2; k++) {
      tmpInput[i][k] = new Array(outW + 2);
    }
  }
  //임시 입력 배열 초기화(127로)
  for (let i = 0; i < inH + 2; i++) {
    for (let k = 0; k < inW + 2; k++) {
      tmpInput[0][i][k] = 127.0;
      tmpInput[1][i][k] = 127.0;
      tmpInput[2][i][k] = 127.0;
    }
  }

  console.log(tmpInput);
  //원입력->임시입력
  for (let rgb = 0; rgb < 3; rgb++) {
    for (let i = 0; i < inH; i++) {
      for (let k = 0; k < inW; k++) {
        tmpInput[rgb][i + 1][k + 1] =  saveImage ? parseFloat(saveImage[rgb][i][k]) :parseFloat(inImage[rgb][i][k]);
      }
    }
  }
  //임시 출력 배열 (출력배열 크기 동일)
  let tmpOutput = new Array(3);
  for (let i = 0; i < 3; i++) {
    tmpOutput[i] = new Array(outH);
    for (let k = 0; k < outH; k++) {
      tmpOutput[i][k] = new Array(outW);
    }
  }

  //회선 연산
  for (let i = 0; i < inH; i++) {
    for (let k = 0; k < inW; k++) {
      //한 점에 대해서 처리
      let s1 = 0.0,
        s2 = 0.0,
        s3 = 0.0;
      for (let m = 0; m < 3; m++) {
        for (let n = 0; n < 3; n++) {
          s1 += tmpInput[0][i + m][k + n] * mask[m][n];
          s2 += tmpInput[1][i + m][k + n] * mask[m][n];
          s3 += tmpInput[2][i + m][k + n] * mask[m][n];
        }
      }
      tmpOutput[0][i][k] = s1;
      tmpOutput[1][i][k] = s2;
      tmpOutput[2][i][k] = s3;
    }
  }
//후처리 마스크 합계가 0이라면 127정도 더하기
for (let i=0; i<outH; i++) {
  for (let k=0; k<outW; k++) {
    tmpOutput[0][i][k] += 127.0;
    tmpOutput[1][i][k] += 127.0;
    tmpOutput[2][i][k] += 127.0;
  }
}
  //임시 출력->원 출력
  for (let i = 0; i < outH; i++) {
    for (let k = 0; k < outW; k++) {
      outImage[0][i][k] = parseInt(tmpOutput[0][i][k]);
      outImage[1][i][k] = parseInt(tmpOutput[1][i][k]);
      outImage[2][i][k] = parseInt(tmpOutput[2][i][k]);
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}


//그레이 스케일
function grayScaleImage() {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH = saveImage ? saveH : inH;
  outW = saveImage ? saveW : inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }
  let r, g, b;

  for (let i = 0; i < inH; i++) {
    for (let k = 0; k < inW; k++) {
      
      if (saveImage != null){
        
      r = saveImage[0][i][k];
      g = saveImage[1][i][k];
      b = saveImage[2][i][k];}
      else{
        r = saveImage[0][i][k];
      g = saveImage[1][i][k];
      b = saveImage[2][i][k];
      }

      let rgb = parseInt((r + g + b) / 3);

      outImage[0][i][k] = rgb;
      outImage[1][i][k] = rgb;
      outImage[2][i][k] = rgb;
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}

function reverseImage() {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH = saveImage ? saveH :inH;
  outW = saveImage ? saveW :inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }
  let r, g, b;

  for (let i = 0; i < inH; i++) {
    for (let k = 0; k < inW; k++) {
      if (saveImage != null){
        r = 255 - saveImage[0][i][k];
      g = 255 - saveImage[1][i][k];
      b = 255 - saveImage[2][i][k];
      }
      else{r = 255 - inImage[0][i][k];
      g = 255 - inImage[1][i][k];
      b = 255 - inImage[2][i][k];}

      outImage[0][i][k] = r;
      outImage[1][i][k] = g;
      outImage[2][i][k] = b;
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}

//흑백
function bwImage() {
  // (중요!) 출력 영상의 크기를 결정... 알고리즘에 따름.
  outH =saveImage ? saveH : inH;
  outW =saveImage ? saveW : inW;
  // 출력 3차원 배열을 준비
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }
  let r, g, b;

  for (let i = 0; i < inH; i++) {
    for (let k = 0; k < inW; k++) {

      if (saveImage != null){ 
        r = saveImage[0][i][k];
        g = saveImage[1][i][k];
        b = saveImage[2][i][k];}
      else {
        r = inImage[0][i][k];
      g = inImage[1][i][k];
      b = inImage[2][i][k];}

      let rgb = parseInt((r + g + b) / 3);
      if (rgb > 127) {
        rgb = 255;
      } else {
        rgb = 0;
      }

      outImage[0][i][k] = rgb;
      outImage[1][i][k] = rgb;
      outImage[2][i][k] = rgb;
    }
  }
  save_Image(outImage, outH, outW);
  displayImage();
}


function endInImage() { // 엔드-인 탐색
  // 중요! 출력 영상의 크기를 계산
  // 중요! 출력 영상의 크기를 계산
  let low, high;

  outH = saveImage ? saveH :inH;
  outW = saveImage ? saveW :inW;
  // 이미지 크기의 2차원 메모리 할당(확보)
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }
  // ** 진짜 영상처리 알고리즘 **
  // outImage = (inImage - low) / (high - low)  * 255

  if (saveImage != null){
    low=[saveImage[0][0][0],saveImage[1][0][0],saveImage[2][0][0]];
    high=[saveImage[0][0][0],saveImage[1][0][0],saveImage[2][0][0]];
  

   for(let rgb=0;rgb<3;rgb++){ 
    for (let i=0; i<outH; i++) {
      for (let k=0; k<outW; k++) {
          if (saveImage[rgb][i][k] < low[rgb])
              low[rgb] = saveImage[rgb][i][k];
          if (saveImage[rgb][i][k] > high[rgb])
              high[rgb] = saveImage[rgb][i][k];
      }
  }  
  low[rgb] += 50;
  high[rgb] -= 50;

}
 
  for (let i=0; i<outH; i++) {
      for (let k=0; k<outW; k++) {
          outImage[0][i][k] = (saveImage[0][i][k] - low[0]) / (high[0] - low[0]) * 255;
          outImage[1][i][k] = (saveImage[1][i][k] - low[1]) / (high[1] - low[1]) * 255;
          outImage[2][i][k] = (saveImage[2][i][k] - low[2]) / (high[2] - low[2]) * 255;
      }
  }
}
else{ 
  low=[inImage[0][0][0],inImage[1][0][0],inImage[2][0][0]];
    high=[inImage[0][0][0],inImage[1][0][0],inImage[2][0][0]];
  

   for(let rgb=0;rgb<3;rgb++){ 
    for (let i=0; i<outH; i++) {
      for (let k=0; k<outW; k++) {
          if (inImage[rgb][i][k] < low[rgb])
              low[rgb] = inImage[rgb][i][k];
          if (inImage[rgb][i][k] > high[rgb])
              high[rgb] = inImage[rgb][i][k];
      }
  }  
  low[rgb] += 50;
  high[rgb] -= 50;

}
 
  for (let i=0; i<outH; i++) {
      for (let k=0; k<outW; k++) {
          outImage[0][i][k] = (inImage[0][i][k] - low[0]) / (high[0] - low[0]) * 255;
          outImage[1][i][k] = (inImage[1][i][k] - low[1]) / (high[1] - low[1]) * 255;
          outImage[2][i][k] = (inImage[2][i][k] - low[2]) / (high[2] - low[2]) * 255;
      }
  }
}
save_Image(outImage, outH, outW);
  displayImage();
}


function histoStImage() { // 히스토그램 스트래칭
  // 중요! 출력 영상의 크기를 계산
  let low, high;

  outH = saveImage ? saveH :inH;
  outW = saveImage ? saveW :inW;
  // 이미지 크기의 2차원 메모리 할당(확보)
  outImage = new Array(3); // 3장짜리 배열 (r, g, b)
  for (let i = 0; i < 3; i++) {
    //1.총 3장만들기 위해서 3번 반복
    outImage[i] = new Array(outH); //2. 1장마다 2차원 배열 생성
    for (let k = 0; k < outH; k++) {
      outImage[i][k] = new Array(outW);
    }
  }
  // ** 진짜 영상처리 알고리즘 **
  // outImage = (inImage - low) / (high - low)  * 255
  if (saveImage != null){
    low=[saveImage[0][0][0],saveImage[1][0][0],saveImage[2][0][0]];
    high=[saveImage[0][0][0],saveImage[1][0][0],saveImage[2][0][0]];
  for(let rgb=0;rgb<3;rgb++){
  for (let i=0; i<inH; i++) {
      for (let k=0; k<inW; k++) {
          if (saveImage[rgb][i][k] < low[rgb])
              low[rgb] = saveImage[rgb][i][k];
          if (saveImage[rgb][i][k] > high[rgb])
              high[rgb] = saveImage[rgb][i][k];
      }
  }  }

  console.log(low,high);
  for(let rgb=0;rgb<3;rgb++){
  for (let i=0; i<inH; i++) {
      for (let k=0; k<inW; k++) {
          outImage[rgb][i][k] = (saveImage[rgb][i][k] - low[rgb]) / (high[rgb] - low[rgb]) * 255;
      }
  }}
  }
  else{  
    low=[inImage[0][0][0],inImage[1][0][0],inImage[2][0][0]];
    high=[inImage[0][0][0],inImage[1][0][0],inImage[2][0][0]];
  for(let rgb=0;rgb<3;rgb++){
  for (let i=0; i<inH; i++) {
      for (let k=0; k<inW; k++) {
          if (inImage[rgb][i][k] < low[rgb])
              low[rgb] = inImage[rgb][i][k];
          if (inImage[rgb][i][k] > high[rgb])
              high[rgb] = inImage[rgb][i][k];
      }
  }  }

 
  for(let rgb=0;rgb<3;rgb++){
  for (let i=0; i<inH; i++) {
      for (let k=0; k<inW; k++) {
          outImage[rgb][i][k] = (inImage[rgb][i][k] - low[rgb]) / (high[rgb] - low[rgb]) * 255;
      }
  }}
}
  save_Image(outImage, outH, outW);
  displayImage();
}
openFile.addEventListener("change", () => {
  init();
  openImage();
});

brightness.addEventListener("change", (e) => {
  console.log(e.target.value);
  brightnessImage(e.target.value);
});
blur.addEventListener("click", () => {
  blur_Image();
});

grayscale.addEventListener("click", () => {
  console.log("clicked");
  grayScaleImage();
});
reverse.addEventListener("click", () => {
  reverseImage();
});
black_white.addEventListener("click", () => {
  bwImage();
});
histoSt.addEventListener("click",()=>{
  histoStImage();
})
embos.addEventListener("click",()=>{
  embosImage();
})
endIn.addEventListener("click",()=>{
  endInImage();
})

// 기본 메뉴

equal.addEventListener("click", () => {
  equal_Image();
});

mirror_lr.addEventListener("click", () => {
  mirrorLR_image();
});

mirror_tb.addEventListener("click", () => {
  mirrorTB_image();
});

rotate90.addEventListener("click", () => {
  rotate90_image();
});

function pick(e) {
  const rect = outCanvas.getBoundingClientRect();
  const x = e.offsetX;
  const y = e.offsetY;

  const pixel = outCtx.getImageData(x, y, 1, 1);
  const data = pixel.data;
  // console.log("(" + x + ", " + y + ") is clicked.");
  const rgba = `rgba(${data[0]}, ${data[1]}, ${data[2]}, ${data[3] / 255})`;

  return rgba;
}
