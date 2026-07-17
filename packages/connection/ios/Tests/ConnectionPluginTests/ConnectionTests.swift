import XCTest
@testable import ConnectionPlugin

class ConnectionTests: XCTestCase {
    func testEcho() {
        let implementation = Connection()
        let value = "Hello, World!"
        let result = implementation.echo(value)

        XCTAssertEqual(value, result)
    }
}
