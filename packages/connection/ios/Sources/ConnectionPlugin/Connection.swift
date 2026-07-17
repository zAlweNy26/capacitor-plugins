import Foundation

@objc public class Connection: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
