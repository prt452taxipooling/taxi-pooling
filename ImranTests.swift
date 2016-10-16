//
//  ImranTests.swift
//  ImranTests
//
//  Created by Imran Khan on 04/09/2016.
//  Copyright © 2016 Imran Khan. All rights reserved.
//

import XCTest
@testable import Imran

class ImranTests: XCTestCase {
    
    override func setUp() {
        super.setUp()
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }
    
    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        super.tearDown()
    }
    func testStringConvertIntoArray()  {
        let arr = abc.convertStringIntoWordsArray("hello Hello abc cba bca aa a adc")
     
            XCTAssert(arr.count == 8)
        
    }
    
    func testCheckWordsCreatedBySameChar()  {
        XCTAssert(abc.checkSameCharacterWords("hello", str2: "ehLol"))
    }
    
    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
    }
    
    func testTotalWords(){
//       XCTAssertFalse(abc.checkCaseSensitive("hello", str2: "eHloL"))
        XCTAssert(abc.checkCaseSensitive("hello", str2: "HelLo"))
        
    }
    
   
    
    
    
}
